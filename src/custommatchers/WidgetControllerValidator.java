package custommatchers;

public class WidgetControllerValidator {

  public interface PostResponse {
    int code();
  }
  public interface Resource {}

  public abstract static class PostMatcher {
    protected PostResponse response;

    public PostMatcher(PostResponse response) { this.response = response; }
    
    public void run() {
      if (!check()) {
        throw new RuntimeException(getErrorMessage());
      }
    }
    
    public abstract String getErrorMessage();
    public abstract boolean check();
  }
  
  
  public static class CreatedPostMatcher extends PostMatcher {
    public CreatedPostMatcher(PostResponse response) {
      super(response);
    }

    @Override public boolean check() {
      return response.code() == 201;
    }
       
    @Override public String getErrorMessage() {
      return "expected code to be 201, and not " + response.code();
    }
  }    
  
  public static class CreatedResourcePostMatcher extends PostMatcher {
    private final Resource resource;

    public CreatedResourcePostMatcher(PostResponse response, Resource resource) {
      super(response);
      this.resource = resource;
    }

    @Override public boolean check() {
      return resource != null && response.code() == 201;
    }
       
    @Override public String getErrorMessage() {
      return response.code() != 201 
        ? "expected code to be 201, and not " + response.code()
        : "resource shoult not be null";
    }
  }
}
