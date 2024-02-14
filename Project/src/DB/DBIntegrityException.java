package DB;

public class DBIntegrityException extends RuntimeException{
    public DBIntegrityException(String msg){
        super(msg);
    }
}
