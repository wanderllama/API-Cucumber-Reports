package util;

public class TestContext {

    private static ThreadLocal<RestAssured> ra = new ThreadLocal<>();

    public static void createRA() {
        ra.set(new RestAssured());
    }

    public static RestAssured getRA() {
        return ra.get();
    }
}
