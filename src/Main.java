import controller.UIController;
import dal.DBConnection;
import dal.UserAdministrationDB;
import lang.Lang;
import ui.TUI;
import lang.Lang;

/**
 * Created by freya on 14-02-2017.
 */
public class Main {
    public static void main(String[] args) {
        Lang.setLanguage(args);
        DBConnection dbc = new DBConnection("sql.wiberg.tech", 3306, "CDIO_del1", "hold12", "2017_h0lD!2");
        UserAdministrationDB uad = new UserAdministrationDB(dbc);

        UIController uic = new UIController(new TUI(), uad);
        uic.run();
//        System.out.print("Progress: ");
//        for (int i = 0; i < 100; i++) {
//            System.out.print(i + "%");
//            try { Thread.sleep(1000); } catch (Exception e) {}
//
//            int iLength = String.valueOf(i).length() + 1;
//            while (iLength-- > 0)
//                System.out.print('\b');
//        }
    }
}
