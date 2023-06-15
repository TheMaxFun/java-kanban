package finalTask.service;

public class GetStatusException extends Exception {
    String s;
    public GetStatusException(String s) {
        this.s = s;
    }
}
