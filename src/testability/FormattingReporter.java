package testability;

import java.io.PrintWriter;


class Reporter {  
  private PrintWriter pw = new PrintWriter(System.out);
  private int n;

  public final void write(String s) {
    pw.println("[" + ++n + "]" + s);
  }
}

// super.* calls inhibit testability. In  the following example, we can't
// easily unit-test FormattingReporter b/c everything it does goes directly 
// to a final method of the superclass. 
public class FormattingReporter extends Reporter {  

  public void writePerson(String first, String middle, String last) {
    super.write(first + " " + middle + " " + last);
  }

  public void writeStudnet(String name, String depatrment) {
    super.write("Student: " + name + " from the " + depatrment + " department");
  }
}

