package shapes;


public class Shapes {
  
  public interface Shape {
    public double area();
  }
    
  public static class Triangle implements Shape {
    public final double base;
    public final double height;
    
    public Triangle(double base, double height) {
      this.base = base;
      this.height = height;
    }

    @Override public double area() {
      return base * height / 2.0;
    }
  }

  public static class Rectangle implements Shape {
    public final double width;
    public final double height;
    
    public Rectangle(double width, double height) {
      this.width = width;
      this.height = height;
    }

    @Override public double area() {
      return width * height;
    }
  }

  public static class Circle implements Shape {
    public final double radius;
    
    public Circle(double radius) {
      this.radius = radius;
    }

    @Override public double area() {
      return Math.PI * radius * radius;
    }
  }  
}
