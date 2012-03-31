package custommatchers;

public class Validators {
  public abstract static class Validator {
    protected Response response;

    public Validator(Response response) { this.response = response; }
    
    public void run() {
      if (!check()) {
        throw new RuntimeException(getErrorMessage());
      }
    }
    
    public abstract String getErrorMessage();
    public abstract boolean check();
  }
  
  public static class CreatedValidator extends Validator {
    public CreatedValidator(Response response) {
      super(response);
    }

    @Override public boolean check() {
      return response.code() == 201;
    }
       
    @Override public String getErrorMessage() {
      return "expected code to be 201, and not " + response.code();
    }
  }    
  
  public static class CreatedResourceValidator extends Validator {
    public CreatedResourceValidator(Response response, Entity resource) {
      super(response);
    }

    @Override public boolean check() {
      return response.createdEntityId() != null && response.code() == 201;
    }
       
    @Override public String getErrorMessage() {
      if (response.code() != 201)  
        return "expected code to be 201, and not " + response.code();
      else
        return "entity ID shoult not be null";
    }
  }
}
