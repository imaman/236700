package shapes;


public class Shapes {
  
  public static class Triangle {
    public final double base;
    public final double height;
    
    public Triangle(double base, double height) {
      this.base = base;
      this.height = height;
    }
  }

  public static class Rectangle {
    public final double width;
    public final double height;
    
    public Rectangle(double width, double height) {
      this.width = width;
      this.height = height;
    }
  }

  public static class Circle {
    public final double radius;
    
    public Circle(double radius) {
      this.radius = radius;
    }
  }
}
