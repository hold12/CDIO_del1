import dal.DBConnection;
import dal.IUserAdministration;
import dal.UserAdministrationDB;
import dto.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by freya on 14-02-2017.
 */
public class Main {
    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection(
                "sql.wiberg.tech",
                3306,
                "CDIO_del1",
                "hold12",
                "2017_h0lD!2"
        );

        List<User> users = new ArrayList<>();

        UserAdministrationDB useradm = new UserAdministrationDB(dbConnection);
        try {
            users = useradm.getUserList();
        } catch (IUserAdministration.DataAccessException e) {
            System.err.println("Error");
        }

        for(User u : users) {
            System.out.println(u.toString());
        }
    }
}
