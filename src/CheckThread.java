import java.lang.Thread;
import java.util.Vector;

/**
 *  This thread checks the online status of users
 *  ids[] = Contains list of ids which are used for accessing onlineStatus.do
 *  needUpdate = Tells if we need Update, then doUpdate is called in parent.
 *  tableModel = The TableModel from parent, we change the status variable from here, then
 *      parent repaints update.
 *  URL = Connection URL
 *  kill = Set to true when wanting to kill the thread;
 */
public class CheckThread extends Thread
{
    //Parent
    private StickStatus parent;
    //Stuff
    private boolean needUpdate = false; //DO NOT confuse with dataUpdate, used for shared memory
    //needUpdate - lets function know parent requires an update in graphics
    private Vector<String> ids;
    private javax.swing.table.DefaultTableModel tableModel;
    
    private java.net.URL url;
    private boolean kill = false;
    //Thread: Shared memory update
    public Vector<String> updateIds = null;
    public javax.swing.table.DefaultTableModel updateModel = null; 
    public boolean dataUpdate = false; //STRICTLY used for shared memory update
    //Consts
    final private String baseCheck = "http://www.stickam.com/onlineStatus.do?id=";
    
    public CheckThread(StickStatus p, Vector<String> i,javax.swing.table.DefaultTableModel t)
    {
        super("CheckThread");
        this.ids = i;
        this.tableModel = t;
        this.parent = p;
        this.setPriority(7);
    }
    
    private void dataUpdate() //Update model and id data safely
    {
        tableModel = updateModel;
        ids = updateIds;
        updateModel = null;
        updateIds = null;
        dataUpdate = false; //IMPORTANT: needs reset to false every time or you won't update
    }
    public void kill() //Called by parent to kill thread
    {
        kill = true;
        this.interrupt();
    }
    
    public void run()
    {
        while(true) {
            for(int i=0;i<ids.size();i++) {
                if(kill)
                    return;
                if(dataUpdate) {
                    dataUpdate();
                    break;
                }                    
                //Get current user's status online
                try {
                    url = new java.net.URL(baseCheck + ids.get(i));
                }
                catch(java.net.MalformedURLException e) {
                    e.printStackTrace();
                    System.out.println("URL:" + baseCheck + ids.get(i));
                    continue;
                }
                //Get data stream
                String data = "";
                byte[] b = new byte[256];
                java.io.InputStream dataStream;
                try {
                    if(!kill)
                        dataStream = url.openStream();
                    else
                        return;
                }
                catch(java.io.IOException e) {
                    e.printStackTrace();
                    continue;
                }
                try {
                    int read = -1;
                    int count = 0;
                    while(true) {
                        if(!kill) {
                            read = dataStream.read(b,0,256);
                            if(read < 0)
                                break;
                            data = data.concat(new String(b));

                        }
                        else
                            return;
                    }
                    b = null;
                }
                catch(java.io.IOException e) {
                    e.printStackTrace();
                    continue;
                }
                //Check if data is a String type
                //If so then check for status (Online/Live/Offline)
                //Unknown is thrown if data that produces none of the defaults is received
                if(data.indexOf("ONLINE") > 0) {
                    if(!tableModel.getValueAt(i,1).equals("Online")) {
                        parent.log("Online",i);
                        needUpdate = true;
                        tableModel.setValueAt("Online",i,1);
                    }
                }
                else if(data.indexOf("LIVE") > 0) {
                    if(!tableModel.getValueAt(i,1).equals("Live")) {
                        parent.log("Live",i);
                        needUpdate = true;
                        tableModel.setValueAt("Live",i,1);
                    }
                }
                else if(data.indexOf("last login:") > 0) {
                    if(!tableModel.getValueAt(i,1).equals("Offline")) {
                        parent.log("Offline",i);
                        needUpdate = true;
                        tableModel.setValueAt("Offline",i,1);
                    }
                }
                else {
                    if(!tableModel.getValueAt(i,1).equals("Unknown")) {
                        needUpdate = true;
                        tableModel.setValueAt("Unknown",i,1);
                    }
                }
                //Check to make sure shared memory wasn't udated, current memory is now out of date
                //DO NOT UPDATE parent
                if(dataUpdate) {
                    dataUpdate();
                    break; //Restart check loop
                }
                //Check if we updated and if so tell parent app to update display
                if(needUpdate && !kill) {
                    needUpdate = false;
                    parent.doUpdate(i); //Tell parent to update display of user;
                }
                //Rest for 5seconds before trying next user. Don't want to hammer
                try {
                    if(!kill)
                        this.sleep(5000);
                    else
                        return;
                }
                catch(InterruptedException e) {
                    if(kill)
                        return;
                    else
                        continue;
                }
            }  
            try {
                if(!kill)
                    this.sleep(15000);
                else
                    return;
            }
            catch(InterruptedException e) {
                if(kill)
                    return;
                else
                    continue;
            }
        }
    }
}
