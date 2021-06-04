package blanco.sample.batchprocess;

import java.io.IOException;

import blanco.sample.batchprocess.valueobject.Sample2ProcessInput;

/**
 * A dummy class for passing the sample batch process compilation.
 */
public class Sample2ProcessImpl implements Sample2Process {
    /**
     * The entry point for intastantiating the class and executing the process.
     * 
     * @param input
     *            Input parameters for a process.
     * @return Result of the process.
     * @throws IOException
     *             If an I/O exception occurs.
     * @throws IllegalArgumentException
     *             If an invalid input value is found.
     */
    public int execute(Sample2ProcessInput input) throws IOException,
            IllegalArgumentException {
        // No particular process.
        return 0;
    }

    /**
     * Whenever an item is processed in the process, it is called back as a progress report.
     * 
     * @param argProgressMessage
     *            Message about the item currently processed.
     * @return It is false if you want to continue the process, or it is true  if you want to request to suspend the process.
     */
    public boolean progress(String argProgressMessage) {
        return false;
    }
}
