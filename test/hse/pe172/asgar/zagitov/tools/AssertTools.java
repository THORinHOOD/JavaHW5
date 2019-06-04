package hse.pe172.asgar.zagitov.tools;

public class AssertTools {

    public static void assertDoesNotThrow(FailingRunnable action){
        try{
            action.run();
        }
        catch(Exception ex){
            throw new Error("Exception was thrown!", ex);
        }
    }

    @FunctionalInterface
    public interface FailingRunnable {
        void run() throws Exception;
    }
}
