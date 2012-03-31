/**
 * 
 */
package custommatchers;

public interface Response {
  int code();
  String entityId();
  boolean createdSuccessfully();
}