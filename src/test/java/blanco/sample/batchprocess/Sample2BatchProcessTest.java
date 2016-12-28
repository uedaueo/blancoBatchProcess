package blanco.sample.batchprocess;

import junit.framework.TestCase;

public class Sample2BatchProcessTest extends TestCase {

    public void testMain() {
        if (false) {
            // このJUnitを実行すると、内部的に System.exitが実行されます。
            final String[] args = { "-require=必須項目", "-field_int_req=123",
                    "-field_long_req=456", "-field_decimal_req=123.456" };
//            Sample2BatchProcess.main(args);
        }

        if (false) {
            // このJUnitを実行すると、内部的に System.exitが実行されます。
            final String[] args = { "-require=必須項目", "-field_int_req=123",
                    "-field_long_req=456", "-field_decimal_req=パース不能",
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
