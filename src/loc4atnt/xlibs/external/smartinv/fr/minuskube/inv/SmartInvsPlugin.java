package loc4atnt.xlibs.external.smartinv.fr.minuskube.inv;

public class SmartInvsPlugin {

    private static SmartInvsPlugin instance;
    private static InventoryManager invManager;

    public void register() {
        instance = this;

        invManager = new InventoryManager();
        invManager.init();
    }

    public static InventoryManager manager() { return invManager; }
    public static SmartInvsPlugin instance() { return instance; }

}
