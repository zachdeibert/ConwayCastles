package com.gitlab.zachdeibert.conwaycastles.options;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;

public final class Options implements Serializable {
    private static final long   serialVersionUID = 9054648043882273621L;
    public static final Options DEFAULT          = load(defaultSaveFile());
    private String filename;
    
    @Option(title="Player 1 Cell", group="Alive Cell Colors", type=ColorOptionPanel.class)
    public Color                myAliveColor;
    @Option(title="Player 2 Cell", group="Alive Cell Colors", type=ColorOptionPanel.class)
    public Color                yourAliveColor;
    @Option(title="Neutral Cell", group="Alive Cell Colors", type=ColorOptionPanel.class)
    public Color                neutralAliveColor;
    @Option(title="Block Cell", group="Alive Cell Colors", type=ColorOptionPanel.class)
    public Color                blockAliveColor;
    @Option(title="Player 1 Color 1", group="Cell Trail Colors", type=ColorOptionPanel.class)
    public Color                myTrail2Color;
    @Option(title="Player 2 Color 1", group="Cell Trail Colors", type=ColorOptionPanel.class)
    public Color                yourTrail2Color;
    @Option(title="Neutral Color 1", group="Cell Trail Colors", type=ColorOptionPanel.class)
    public Color                neutralTrail2Color;
    @Option(title="Block Color 1", group="Cell Trail Colors", type=ColorOptionPanel.class)
    public Color                blockTrail1Color;
    @Option(title="Player 1 Color 2", group="Cell Trail Colors", type=ColorOptionPanel.class)
    public Color                myTrail1Color;
    @Option(title="Player 2 Color 2", group="Cell Trail Colors", type=ColorOptionPanel.class)
    public Color                yourTrail1Color;
    @Option(title="Neutral Color 2", group="Cell Trail Colors", type=ColorOptionPanel.class)
    public Color                neutralTrail1Color;
    @Option(title="Empty Cell Color", group="Display", type=ColorOptionPanel.class)
    public Color                voidColor;
    @Option(title="Player 1 Color 3", group="Cell Trail Colors", type=ColorOptionPanel.class)
    public Color                myTrail0Color;
    @Option(title="Player 2 Color 3", group="Cell Trail Colors", type=ColorOptionPanel.class)
    public Color                yourTrail0Color;
    @Option(title="Neutral Color 3", group="Cell Trail Colors", type=ColorOptionPanel.class)
    public Color                neutralTrail0Color;
    @Option(title="Block Color 2", group="Cell Trail Colors", type=ColorOptionPanel.class)
    public Color                blockTrail0Color;
    @Option(title="Border Color", group="Display", type=ColorOptionPanel.class)
    public Color                borderColor;
    @Option(title="Your Name", group="Other", type=TextOptionPanel.class)
    public String               name;
    @Option(title="Ticks per Second", group="Other", type=TextOptionPanel.class)
    public int                  tps;
    
    public static String defaultSaveFile() {
        final Map<String, String> env = System.getenv();
        if ( env.containsKey("appdata") ) {
            return String.format("%s%cConwayCastles.ser", env.get("appdata"), File.separatorChar);
        } else if ( env.containsKey("HOME") ) {
            return String.format("%s%c.ConwayCastles.ser", env.get("HOME"), File.separatorChar);
        }
        return ".ConwayCastles.ser";
    }
    
    public static void save(final Options obj, final String filename) {
        try {
            final FileOutputStream fos = new FileOutputStream(new File(filename));
            final ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.close();
            fos.close();
        } catch ( final Exception ex ) {
            ex.printStackTrace();
        }
    }
    
    public static void save(final Options obj) {
        if ( obj.filename == null ) {
            throw new IllegalArgumentException("Filename not found");
        }
        save(obj, obj.filename);
    }
    
    public static Options load(final String filename) {
        try {
            final FileInputStream fis = new FileInputStream(new File(filename));
            final ObjectInputStream ois = new ObjectInputStream(fis);
            final Options obj = (Options) ois.readObject();
            ois.close();
            fis.close();
            return obj;
        } catch ( final Exception ex ) {
            ex.printStackTrace();
        }
        final Options obj = load();
        obj.filename = filename;
        return obj;
    }
    
    public static Options load() {
        return new Options();
    }
    
    @Override
    protected void finalize() throws Throwable {
        if ( filename != null ) {
            save(this);
        }
        super.finalize();
    }

    private Options() {
        myAliveColor = new Color(0, 0, 255);
        yourAliveColor = new Color(255, 0, 0);
        neutralAliveColor = new Color(255, 0, 255);
        blockAliveColor = new Color(0, 255, 0);
        myTrail2Color = new Color(127, 127, 255);
        yourTrail2Color = new Color(255, 127, 127);
        neutralTrail2Color = new Color(255, 127, 255);
        blockTrail1Color = new Color(127, 255, 127);
        myTrail1Color = new Color(171, 171, 255);
        yourTrail1Color = new Color(255, 171, 171);
        neutralTrail1Color = new Color(255, 171, 255);
        voidColor = new Color(255, 255, 255);
        myTrail0Color = new Color(213, 213, 255);
        yourTrail0Color = new Color(255, 213, 213);
        neutralTrail0Color = new Color(255, 213, 255);
        blockTrail0Color = new Color(191, 255, 191);
        borderColor = new Color(127, 127, 127, 127);
        name = "Nobdy";
        tps = 10;
        filename = null;
    }
}
