import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.util.Vector;
import java.awt.Color;

public class OptionsData
{
    //User Data
    private Vector<String> names;
    private Vector<String> ids;
    private Vector<Boolean> alertOnChange;
    //Color Defaults
    final private Color OFFLINE_COLOR = Color.gray;
    final private Color ONLINE_COLOR = Color.white;
    final private Color LIVE_COLOR = Color.green;
    final private Color UNKNOWN_COLOR = Color.red;
    final private Color TEXT_COLOR = Color.black;
    //Background Colors
    private Color onlineColor;
    private Color offlineColor;
    private Color liveColor;
    private Color unknownColor;
    //Foreground Colors
    private Color onlineTextColor;
    private Color offlineTextColor;
    private Color liveTextColor;
    private Color unknownTextColor;

    //Other Options
    private boolean sortName = false;
    private boolean autoStart = true;
    private boolean minimizeTray = true;
    
    //Logging
    private String logFile;
    private boolean loggingEnabled = false;
    private boolean logOnline = false;
    private boolean logOffline = false;
    private boolean logLive = false;
    
    public OptionsData()
    {
        names = new Vector<String>();
        ids = new Vector<String>();
        alertOnChange = new Vector<Boolean>();
        onlineColor = ONLINE_COLOR;
        offlineColor = OFFLINE_COLOR;
        liveColor = LIVE_COLOR;
        unknownColor = UNKNOWN_COLOR;
        onlineTextColor = TEXT_COLOR;
        offlineTextColor = TEXT_COLOR;
        liveTextColor = TEXT_COLOR;
        unknownTextColor = TEXT_COLOR;
        //Other Options
        sortName = false;
        autoStart = true;
        minimizeTray = true;
        //Logging Options
        logFile = "log.log";
        loggingEnabled = false;
        logOnline = false;
        logOffline = false;
        logLive = false;
    }
    
    public static boolean serializeOut(String file, OptionsData data)
    {
        FileOutputStream streamOut;
        ObjectOutputStream dataOut;
        new File(file).delete();
        
        try
        {
            new File(file).createNewFile();
            streamOut = new FileOutputStream(file);
            dataOut = new ObjectOutputStream(streamOut);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.err.println("Error: Fail open Write streams");
            return false;
        }
        
        try
        {
            dataOut.writeObject(data.getNames());
            dataOut.writeObject(data.getIds());
            dataOut.writeObject(data.getAlertOnChange());
            dataOut.writeObject(data.getOnlineColor());
            dataOut.writeObject(data.getOfflineColor());
            dataOut.writeObject(data.getLiveColor());
            dataOut.writeObject(data.getUnknownColor());
            dataOut.writeObject(data.getOnlineTextColor());
            dataOut.writeObject(data.getOfflineTextColor());
            dataOut.writeObject(data.getLiveTextColor());
            dataOut.writeObject(data.getUnknownTextColor());
            dataOut.writeBoolean(data.getSortName());
            dataOut.writeBoolean(data.getAutoStart());
            dataOut.writeBoolean(data.getMinimizeTray());
            dataOut.writeObject(data.getLogFile());
            dataOut.writeBoolean(data.getLoggingEnabled());
            dataOut.writeBoolean(data.getLogOnline());
            dataOut.writeBoolean(data.getLogOffline());
            dataOut.writeBoolean(data.getLogLive());
            
            //Close file
            dataOut.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.err.println("Error: Failed to write data");
            return false;
        }
        return true;
    }
    
    public static OptionsData serializeIn(String file)
    {
        OptionsData newData = new OptionsData();
        if(!new File(file).exists())
        {
            System.err.println("Data file does not exist");
            return newData;
        }
        FileInputStream streamIn;
        ObjectInputStream dataIn;
        try
        {
            streamIn = new FileInputStream(file);
            dataIn = new ObjectInputStream(streamIn);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.err.println("Error: Failed to open Read streams");
            return newData;
        }
        
        try
        {
            Object temp;
            //Names
            temp = dataIn.readObject();
            if(temp.getClass() == Vector.class && temp != null)
                newData.setNames((Vector<String>)temp);
            //IDs
            temp = dataIn.readObject();
            if(temp.getClass() == Vector.class && temp != null)
                newData.setIds((Vector<String>) temp);
            //Alert Option
            temp = dataIn.readObject();
            if( temp.getClass() == Vector.class && temp != null)
                newData.setAlertOnChange((Vector<Boolean>) temp);
            //Background Colors
            temp = dataIn.readObject(); //Online color
            if (temp.getClass() == Color.class && temp != null)
                newData.setOnlineColor(((Color)temp));
            temp = dataIn.readObject(); //Offline color
            if (temp.getClass() == Color.class && temp != null)
                newData.setOfflineColor(((Color)temp));
            temp = dataIn.readObject(); //Live
            if (temp.getClass() == Color.class && temp != null)
                newData.setLiveColor(((Color)temp));
            temp = dataIn.readObject(); //Unknown
            if (temp.getClass() == Color.class && temp != null)
                newData.setUnknownColor(((Color)temp));
            //Foreground Colors
            temp = dataIn.readObject(); //Online Text
            if (temp.getClass() == Color.class && temp != null)
                newData.setOnlineTextColor(((Color)temp));
            temp = dataIn.readObject(); //Offline Text
            if (temp.getClass() == Color.class && temp != null)
                newData.setOfflineTextColor(((Color)temp));
            temp = dataIn.readObject(); //Live Text
            if (temp.getClass() == Color.class && temp != null)
                newData.setLiveTextColor(((Color)temp));
            temp = dataIn.readObject(); //Unknown Text
            if (temp.getClass() == Color.class && temp != null)
                newData.setUnknownTextColor(((Color)temp));
            
            //Other Data - Read in
            newData.setSortName(dataIn.readBoolean());
            newData.setAutoStart(dataIn.readBoolean());
            newData.setMinimizeTray(dataIn.readBoolean());
            //Logging Data
            temp = dataIn.readObject();
            if(temp.getClass() == String.class && temp != null)
                newData.setLogFile((String)temp);
            newData.setLoggingEnabled(dataIn.readBoolean());
            newData.setLogOnline(dataIn.readBoolean());
            newData.setLogOffline(dataIn.readBoolean());
            newData.setLogLive(dataIn.readBoolean());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.err.println("Error reading data in");
            return newData;
        }
        
        return newData;
    }
    
    public void setUnknownColor(Color unknownColor)
    { this.unknownColor = unknownColor; }
    public Color getUnknownColor()
    { return unknownColor; }
    public void setOnlineColor(Color onlineColor)
    { this.onlineColor = onlineColor; }
    public void setOfflineColor(Color offlineColor)
    { this.offlineColor = offlineColor; }
    public void setLiveColor(Color liveColor)
    { this.liveColor = liveColor; }
    public Color getOnlineColor()
    { return onlineColor; }
    public Color getOfflineColor()
    { return offlineColor; }
    public Color getLiveColor()
    { return liveColor; }
    public Color getOnlineTextColor()
    { return onlineTextColor; }
    public Color getOfflineTextColor()
    { return offlineTextColor; }
    public Color getLiveTextColor()
    { return liveTextColor; }
    public Color getUnknownTextColor()
    { return unknownTextColor; }
    public void setOnlineTextColor(Color c)
    { this.onlineTextColor = c; }
    public void setOfflineTextColor(Color c)
    { this.offlineTextColor = c; }
    public void setLiveTextColor(Color c)
    { this.liveTextColor = c; }
    public void setUnknownTextColor(Color c)
    { this.unknownTextColor = c; }
    public Vector<String> getIds()
    { return ids; }
    public Vector<String> getNames()
    { return names; }
    public void setIds(Vector<String> ids)
    { this.ids = ids; }
    public void setNames(Vector<String> names)
    { this.names = names; }
    public boolean getSortName()
    { return sortName; }
    public void setSortName(boolean s)
    { this.sortName = s; }
    public boolean getAutoStart()
    { return autoStart; }
    public void setAutoStart(boolean b)
    { this.autoStart = b; }
    public boolean getMinimizeTray() { return this.minimizeTray; }
    public void setMinimizeTray(boolean b) { this.minimizeTray = b; }
    
    public String getLogFile() { return this.logFile; }
    public boolean getLoggingEnabled() { return this.loggingEnabled; }
    public boolean getLogOnline() { return this.logOnline; }
    public boolean getLogOffline() { return this.logOffline; }
    public boolean getLogLive() { return this.logLive; }
    public void setLogFile(String s) { this.logFile = s; }
    public void setLoggingEnabled(boolean b) { this.loggingEnabled = b; }
    public void setLogOnline(boolean b) { this.logOnline = b; }
    public void setLogOffline(boolean b) { this.logOffline = b; }
    public void setLogLive(boolean b) { this.logLive = b; }
    
    public void add(String n, String id)
    {
        names.addElement(n);
        ids.addElement(id);
        alertOnChange.addElement(new Boolean(false));
    }
    
    public void add(String n, String id, Boolean a)
    {
        names.addElement(n);
        ids.addElement(id);
        alertOnChange.addElement(a);
    }
    public void remove(int i)
    {
        names.remove(i);
        ids.remove(i);
        alertOnChange.remove(i);
    }
    
    public void editName(int i, String n)
    {
        names.set(i,n);
    }
    public void editName(String cur, String n)
    {
        int i = names.indexOf(cur);
        if(i == -1)
            return;
        this.editName(i,n);
    }
    public void editId(int i, String id)
    {
        ids.set(i,id);
    }
    public void editId(String curID, String id)
    {
        int i = ids.indexOf(curID);
        if(i == -1)
            return;
        this.editId(i,id);
    }
    
    public Vector<Boolean> getAlertOnChange()
    { return alertOnChange; }
    public void setAlertOnChange(Vector<Boolean> a)
    { this.alertOnChange = a; }
    public void setAlert(int i, boolean value)
    { alertOnChange.set(i,new Boolean(value)); }
    public boolean getAlert(int i)
    { return alertOnChange.get(i).booleanValue(); }
    
    public void resetDefault()
    {
        onlineColor = ONLINE_COLOR;
        offlineColor = OFFLINE_COLOR;
        liveColor = LIVE_COLOR;
        unknownColor = UNKNOWN_COLOR;
        onlineTextColor = TEXT_COLOR;
        offlineTextColor = TEXT_COLOR;
        liveTextColor = TEXT_COLOR;
        unknownTextColor = TEXT_COLOR;
        sortName = false;
        autoStart = true;
        logFile = "log.log";
        loggingEnabled = false;
        logOnline = false;
        logOffline = false;
        logLive = false;
    }
}
