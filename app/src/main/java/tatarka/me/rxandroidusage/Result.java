package tatarka.me.rxandroidusage;

public class Result<T> {

    public static <T> Result<T> success(T value) {
        return new Result<T>(value);
    }

    public static <T> Result<T> error(Throwable error) {
        return new Result<T>(error);
    }

    private final T success;
    private final Throwable error;
    private final boolean isSuccess;

    private Result(T success) {
        this.success = success;
        this.error = null;
        isSuccess = true;
    }

    private Result(Throwable error) {
        this.success = null;
        this.error = error;
        isSuccess = false;
    }

    public T get() throws Throwable {
        if (isSuccess) {
            return success;
        } else {
            throw error;
        }
    }
}
