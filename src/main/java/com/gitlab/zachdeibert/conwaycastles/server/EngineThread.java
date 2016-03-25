package com.gitlab.zachdeibert.conwaycastles.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.gitlab.zachdeibert.conwaycastles.GameModel;
import com.gitlab.zachdeibert.conwaycastles.NetworkConstants;
import com.gitlab.zachdeibert.conwaycastles.Packet;
import com.gitlab.zachdeibert.conwaycastles.Side;
import com.gitlab.zachdeibert.conwaycastles.options.Options;
import com.gitlab.zachdeibert.conwaycastles.simulation.SimulationWindow;
import com.gitlab.zachdeibert.jnet.NetworkServer;

final class EngineThread extends Thread {
    private final NetworkServer    server;
    private final SimulationWindow win;
    private final GameModel        model;
    private final List<Action>     actions;
    private long                   millisParts;
    private long                   lastTick;
    
    private final byte get(final byte[] d, final int x, final int y) {
        final int o = y * GameModel.WIDTH + x;
        final int i = o / 2;
        if ( o % 2 == 0 ) {
            return (byte) (d[i] >> 4);
        } else {
            return (byte) (d[i] & 0x0F);
        }
    }
    
    private final void set(final byte[] d, final byte v, final int x, final int y) {
        final int o = y * GameModel.WIDTH + x;
        final int i = o / 2;
        if ( o % 2 == 0 ) {
            d[i] = (byte) ( (d[i] & 0x0F) | (v << 4));
        } else {
            d[i] = (byte) ( (d[i] & 0xF0) | (v & 0x0F));
        }
    }
    
    private final boolean isAlive(final byte[] d, final int x, final int y) {
        if ( x < 0 || x >= GameModel.WIDTH || y < 0 || y >= GameModel.HEIGHT ) {
            return false;
        } else {
            final byte v = get(d, x, y);
            return v >= 0 && v <= 3;
        }
    }
    
    private final int countNeighbors(final byte[] d, final int x, final int y) {
        //formatter:off
        return (isAlive(d, x - 1, y - 1) ? 1 : 0) + (isAlive(d, x    , y - 1) ? 1 : 0) + (isAlive(d, x + 1, y - 1) ? 1 : 0) +
               (isAlive(d, x - 1, y    ) ? 1 : 0) +                                      (isAlive(d, x + 1, y    ) ? 1 : 0) +
               (isAlive(d, x - 1, y + 1) ? 1 : 0) + (isAlive(d, x    , y + 1) ? 1 : 0) + (isAlive(d, x + 1, y + 1) ? 1 : 0);
        //formatter:on
    }
    
    private final boolean hasNeighbor(final byte[] d, final byte v, final int x, final int y) {
        for ( int cx = x - 1; cx <= x + 1; cx++ ) {
            for ( int cy = y - 1; cy <= y + 1; cy++ ) {
                if ( isAlive(d, cx, cy) && get(d, cx, cy) == v ) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private final byte getColor(final byte[] d, final int x, final int y, final Random rand) {
        boolean a = false;
        boolean b = false;
        boolean block = false;
        for ( int cx = x - 1; cx <= x + 1; cx++ ) {
            for ( int cy = y - 1; cy <= y + 1; cy++ ) {
                if ( isAlive(d, cx, cy) ) {
                    switch ( get(d, cx, cy) ) {
                        case 0:
                            a = true;
                            break;
                        case 1:
                            b = true;
                            break;
                        case 2:
                            return (byte) 2;
                        case 3:
                            block = true;
                    }
                    if ( (a && b) || (b && block) || (block && a) ) {
                        return (byte) 2;
                    }
                }
            }
        }
        if ( a && b ) {
            return (byte) 2;
        } else if ( (a || b) && block ) {
            if ( rand.nextFloat() > 0.75 ) {
                return (byte) 2;
            } else if ( a ) {
                return (byte) 0;
            } else if ( b ) {
                return (byte) 1;
            }
        } else if ( a ) {
            return (byte) 0;
        } else if ( b ) {
            return (byte) 1;
        } else if ( block ) {
            return (byte) 3;
        }
        return (byte) 11;
    }
    
    private final void tick() {
        final long tickTime = System.currentTimeMillis();
        // Update Clock
        if ( lastTick > 0 ) {
            final long delta = tickTime - lastTick;
            millisParts += delta;
            while ( millisParts > 1000 ) {
                model.time++;
                millisParts -= 1000;
            }
        }
        // Handle placed cells
        while ( actions.size() > 0 ) {
            final Action act = actions.get(0);
            actions.remove(0);
            if ( act.isValid(model.data) ) {
                final byte v;
                int cells;
                if ( act.side == Side.Client ) {
                    v = 1;
                    cells = model.cellsB;
                } else {
                    v = 0;
                    cells = model.cellsA;
                }
                for ( int x = 0; x < act.width; x++ ) {
                    for ( int y = 0; y < act.height; y++ ) {
                        final int o = y * act.width + x;
                        final int i = o / 8;
                        final int b = o % 8;
                        if ( (act.data[i] & (1 << b)) != 0 ) {
                            if ( cells < 1 ) {
                                break;
                            }
                            set(model.data, v, x + act.x, y + act.y);
                            cells--;
                        }
                    }
                }
                if ( act.side == Side.Client ) {
                    model.cellsB = cells;
                } else {
                    model.cellsA = cells;
                }
            }
        }
        // Tick simulator
        final byte[] n = new byte[model.data.length];
        // Random is only used for the fading of dead cells and whether to create a neutral cell from a block and player cell
        final Random rand = new Random();
        for ( int x = 0; x < GameModel.WIDTH; x++ ) {
            for ( int y = 0; y < GameModel.HEIGHT; y++ ) {
                final int an = countNeighbors(model.data, x, y);
                byte v = get(model.data, x, y);
                if ( v >= 0 && v < 4 ) {
                    if ( an < 2 || an > 3 ) {
                        if ( v == 0 && y >= GameModel.HEIGHT / 2 && hasNeighbor(model.data, (byte) 1, x, y) ) {
                            model.scoreB++;
                        } else if ( v == 1 && y <= GameModel.HEIGHT / 2 && hasNeighbor(model.data, (byte) 0, x, y) ) {
                            model.scoreA++;
                        }
                        v += 4;
                        set(n, (byte) v, x, y);
                    } else {
                        set(n, v, x, y);
                    }
                } else if ( an == 3 ) {
                    v = getColor(model.data, x, y, rand);
                    set(n, v, x, y);
                    if ( v == 0 ) {
                        model.cellA += 0.0004;
                    } else if ( v == 1 ) {
                        model.cellB += 0.0004;
                    }
                } else {
                    set(n, v, x, y);
                }
                short nv = v;
                if ( nv < 0 ) {
                    nv += 128;
                }
                if ( nv < 4 ) {} else if ( nv < 8 ) {
                    if ( rand.nextFloat() < 0.1 ) {
                        if ( nv == 7 ) {
                            set(n, (byte) 15, x, y);
                        } else {
                            set(n, (byte) (nv + 4), x, y);
                        }
                    }
                } else if ( nv == 11 ) {} else if ( nv < 12 ) {
                    if ( rand.nextFloat() < 0.05 ) {
                        set(n, (byte) (nv + 4), x, y);
                    }
                } else {
                    if ( rand.nextFloat() < 0.01 ) {
                        set(n, (byte) 11, x, y);
                    }
                }
            }
        }
        model.data = n;
        while ( model.cellA > 1 ) {
            model.cellA--;
            model.cellsA++;
        }
        while ( model.cellB > 1 ) {
            model.cellB--;
            model.cellsB++;
        }
        // Update state vars
        lastTick = tickTime;
    }
    
    final void addAction(final Action act) {
        actions.add(act);
    }
    
    @Override
    public final void run() {
        try {
            long lastFrame = 0;
            while ( true ) {
                long frame = System.currentTimeMillis();
                long delta = (long) (1000.0 / (double) Options.DEFAULT.tps);
                if ( frame - lastFrame <= delta ) {
                    Thread.sleep(lastFrame + delta - frame);
                    frame = System.currentTimeMillis();
                }
                lastFrame = frame;
                tick();
                server.sendPacket(new Packet<GameModel>(NetworkConstants.PACKET_MODEL, model));
                win.update(Side.Server, model, Options.DEFAULT);
            }
        } catch ( final InterruptedException ex ) {} catch ( final IOException ex ) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
    
    EngineThread(final NetworkServer server, final SimulationWindow win, final GameModel model) {
        super("Engine-Thread");
        this.server = server;
        this.win = win;
        this.model = model;
        this.actions = new ArrayList<Action>(2);
        millisParts = 0;
        lastTick = 0;
    }
}
