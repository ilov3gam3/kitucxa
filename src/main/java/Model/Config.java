package Model;

import java.util.Map;

public class Config {
    public static Map<String, String> map = Map.ofEntries(
            Map.entry("db_server", "swpdata.database.windows.net:1433"),
            Map.entry("db_name", "kitucxa"),
            Map.entry("db_username", "minh"),
            Map.entry("db_password", "Matkhaulagivaytroi1"),
            Map.entry("email_address", "tranquangminh116@gmail.com"),
            Map.entry("email_address_password", "iahskjpdwuvcyzym"),
            Map.entry("max_electricity", "70"),
            Map.entry("max_water", "20")
    );

}
