import controller.UIController;
import dal.DBConnection;
import dal.UserAdministrationDB;
import lang.Lang;
import ui.TUI;

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
    }
}
