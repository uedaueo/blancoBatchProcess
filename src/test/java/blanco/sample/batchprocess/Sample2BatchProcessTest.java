package blanco.sample.batchprocess;

import junit.framework.TestCase;

public class Sample2BatchProcessTest extends TestCase {

    public void testMain() {
        if (false) {
            // System.exit will be executed internally when you run this JUnit.
            final String[] args = { "-require=Required field", "-field_int_req=123",
                    "-field_long_req=456", "-field_decimal_req=123.456" };
//            Sample2BatchProcess.main(args);
        }

        if (false) {
            // System.exit will be executed internally when you run this JUnit.
            final String[] args = { "-require=Required field", "-field_int_req=123",
                    "-field_long_req=456", "-field_decimal_req=Unparsable",
                    "-field_boolean_req=false" };
  //          Sample2BatchProcess.main(args);
        }
    }

    public void testExecute() {
    }

    public void testUsage() {
    }

    public void testValidateInput() {
    }
}
