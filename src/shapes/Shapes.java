package shapes;

import java.awt.geom.Point2D;

public class Shapes {
  
  public static class Triangle {
    public final Point2D vertexA;
    public final Point2D vertexB;
    public final Point2D vertexC;
    
    public Triangle(Point2D vertexA, Point2D vertexB, Point2D vertexC) {
      this.vertexA = vertexA;
      this.vertexB = vertexB;
      this.vertexC = vertexC;
    }
  }

  public static class Rectangle {
    public final Point2D center;
    public final double width;
    public final double height;
    
    public Rectangle(Point2D center, double width, double height) {
      this.center = center;
      this.width = width;
      this.height = height;
    }
  }

  public static class Circle {
    public final Point2D center;
    public final double radius;
    
    public Circle(Point2D center, double radius) {
      this.center = center;
      this.radius = radius;
    }
  }
}
