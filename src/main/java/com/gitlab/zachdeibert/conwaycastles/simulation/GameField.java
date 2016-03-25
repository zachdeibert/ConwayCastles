package com.gitlab.zachdeibert.conwaycastles.simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import com.gitlab.zachdeibert.conwaycastles.ActionData;
import com.gitlab.zachdeibert.conwaycastles.GameModel;
import com.gitlab.zachdeibert.conwaycastles.Side;
import com.gitlab.zachdeibert.conwaycastles.options.Options;

final class GameField extends AbstractDisplayItem {
    public static final int          BLOCK_SIZE       = 16;
    private static final long        serialVersionUID = 2994823580238002476L;
    private final BuildFinishedEvent handler;
    private final byte[]             building;
    private boolean                  rotate180;
    private GameModel                model;
    private Options                  opts;
    private boolean                  buildMode;
    private int                      builtCells;
    
    @Override
    void update(final Side side, final GameModel model, final Options opts) {
        rotate180 = side == Side.Client;
        this.model = model;
        this.opts = opts;
        repaint();
    }
    
    @Override
    void enterBuildMode() {
        buildMode = true;
        for ( int i = 0; i < building.length; i++ ) {
            building[i] = 0;
        }
        builtCells = 0;
    }
    
    @Override
    void leaveBuildMode() {
        buildMode = false;
        final BuildingShrinker shrinker = new BuildingShrinker(building);
        final ActionData data;
        if ( (data = shrinker.shrink()) != null ) {
            handler.accept(data);
        }
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BLOCK_SIZE * GameModel.WIDTH, BLOCK_SIZE * GameModel.HEIGHT);
    }
    
    @Override
    public void paintComponent(final Graphics g) {
        if ( model == null || opts == null ) {
            super.paintComponent(g);
        } else {
            final int d;
            final int dh;
            final int dw;
            final int ix;
            final int iy;
            final int mh;
            final int mw;
            final Color[] colorMatrix;
            if ( rotate180 ) {
                d = -1;
                dh = GameModel.HEIGHT;
                dw = GameModel.WIDTH;
                mh = 0;
                mw = 0;
                ix = GameModel.WIDTH - 1;
                iy = GameModel.HEIGHT - 1;
                colorMatrix = new Color[] {
                    opts.yourAliveColor,
                    opts.myAliveColor,
                    opts.neutralAliveColor,
                    opts.blockAliveColor,
                    
                    opts.yourTrail2Color,
                    opts.myTrail2Color,
                    opts.neutralTrail2Color,
                    opts.blockTrail1Color,
                    
                    opts.yourTrail1Color,
                    opts.myTrail1Color,
                    opts.neutralTrail1Color,
                    opts.voidColor,
                    
                    opts.yourTrail0Color,
                    opts.myTrail0Color,
                    opts.neutralTrail0Color,
                    opts.blockTrail0Color
                };
            } else {
                d = 1;
                dh = -GameModel.HEIGHT;
                dw = -GameModel.WIDTH;
                mh = GameModel.HEIGHT - 1;
                mw = GameModel.WIDTH - 1;
                ix = 0;
                iy = 0;
                colorMatrix = new Color[] {
                    opts.myAliveColor,
                    opts.yourAliveColor,
                    opts.neutralAliveColor,
                    opts.blockAliveColor,
                    
                    opts.myTrail2Color,
                    opts.yourTrail2Color,
                    opts.neutralTrail2Color,
                    opts.blockTrail1Color,
                    
                    opts.myTrail1Color,
                    opts.yourTrail1Color,
                    opts.neutralTrail1Color,
                    opts.voidColor,
                    
                    opts.myTrail0Color,
                    opts.yourTrail0Color,
                    opts.neutralTrail0Color,
                    opts.blockTrail0Color
                };
            }
            int x = ix;
            int y = iy;
            for ( byte c : model.data ) {
                byte a = (byte) ( (c & 0xF0) >> 4);
                byte b = (byte) (c & 0x0F);
                g.setColor(colorMatrix[a]);
                g.fillRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                g.setColor(opts.borderColor);
                g.drawRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                x += d;
                if ( x * d > mw ) {
                    x += dw;
                    y += d;
                }
                if ( y * d > mh ) {
                    y += dh;
                }
                g.setColor(colorMatrix[b]);
                g.fillRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                g.setColor(opts.borderColor);
                g.drawRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                x += d;
                if ( x * d > mw ) {
                    x += dw;
                    y += d;
                }
                if ( y * d > mh ) {
                    y += dh;
                }
            }
            if ( buildMode ) {
                g.setColor(new Color(0, 0, 0, 63));
                g.fillRect(0, 0, GameModel.WIDTH * BLOCK_SIZE, GameModel.HEIGHT * BLOCK_SIZE);
                g.setColor(opts.myAliveColor);
                x = ix;
                y = iy;
                for ( byte b : building ) {
                    for ( int i = 0; i < 8; i++ ) {
                        final byte a = (byte) (b & (1 << i));
                        if ( a != 0 ) {
                            g.fillRect(x * BLOCK_SIZE + 1, y * BLOCK_SIZE + 1, BLOCK_SIZE - 1, BLOCK_SIZE - 1);
                        }
                        x += d;
                        if ( x * d > mw ) {
                            x += dw;
                            y += d;
                        }
                        if ( y * d > mh ) {
                            y += dh;
                        }
                    }
                }
            }
        }
    }
    
    void clickedAt(final int x, final int y) {
        if ( buildMode && builtCells < (rotate180 ? model.cellsB : model.cellsA) ) {
            final int bx;
            final int by;
            if ( rotate180 ) {
                bx = GameModel.WIDTH - x / BLOCK_SIZE - 1;
                by = GameModel.HEIGHT - y / BLOCK_SIZE - 1;
            } else {
                bx = x / BLOCK_SIZE;
                by = y / BLOCK_SIZE;
            }
            final int o = by * GameModel.WIDTH + bx;
            final int i = o / 8;
            final byte f = (byte) (1 << (o % 8));
            final byte ov = building[i];
            final byte nv = building[i] ^= f;
            final ActionData act = new BuildingShrinker(building).shrink();
            if ( act == null || act.isValid(model.data, (byte) (rotate180 ? 1 : 0)) ) {
                if ( ( (ov ^ nv) | ov) == nv ) {
                    builtCells++;
                } else {
                    builtCells--;
                }
            } else {
                building[i] ^= f;
            }
        }
    }
    
    GameField(final BuildFinishedEvent handler) {
        this.handler = handler;
        building = new byte[ (GameModel.WIDTH * GameModel.HEIGHT + 7) / 8];
        buildMode = false;
        addMouseListener(new FieldClickListener(this));
    }
}
