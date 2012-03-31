package custommatchers;

public class Validators {

  public abstract static class Dispatcher {
    private final Validator[] validators;
    
    public Dispatcher(Validator... validators) {
      this.validators = validators;
    }
    
    public void run(Request request) {
      Response response = sendRequest();
      for (Validator current : validators) {
        if (!current.check(response)) {
          throw new RuntimeException(current.getErrorMessage(response));
        }
      }
    }

    protected abstract Response sendRequest();
  }
  
  public interface Validator {
    public abstract String getErrorMessage(Response response);
    public abstract boolean check(Response response);
  }
  
  public static class CreatedValidator implements Validator {
    @Override public boolean check(Response response) {
      return response.code() == 201;
    }
       
    @Override public String getErrorMessage(Response response) {
      return "expected code to be 201, and not " + response.code();
    }
  }    
  
  public static class CreatedResourceValidator implements Validator {
    @Override public boolean check(Response response) {
      return response.createdEntityId() != null && response.code() == 201;
    }
       
    @Override public String getErrorMessage(Response response) {
      if (response.code() != 201)  
        return "expected code to be 201, and not " + response.code();
      else
        return "entity ID shoult not be null";
    }
  }
}
