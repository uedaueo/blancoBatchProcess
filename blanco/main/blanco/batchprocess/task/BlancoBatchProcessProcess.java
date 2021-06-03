package blanco.batchprocess.task;

import java.io.IOException;

import blanco.batchprocess.task.valueobject.BlancoBatchProcessProcessInput;

/**
 * Process [BlancoBatchProcessProcess] interface.
 *
 * Inherit this interface and create a [BlancoBatchProcessProcessclass in the [blanco.batchprocess.task] package to implement the actual batch processing.<br>
 */
interface BlancoBatchProcessProcess {
    /**
     * The entry point for intastantiating the class and executing the process.
     *
     * @param input Innput parameters for a process.
     * @return Result of the process.
     * @throws IOException If an I/O exception occurs.
     * @throws IllegalArgumentException If an invalid input value is found.
     */
    int execute(final BlancoBatchProcessProcessInput input) throws IOException, IllegalArgumentException;

    /**
     * Whenever an item is processed in the process, it is called back as a progress report.
     *
     * @param argProgressMessage Message about the item currently processed.
     * @return It is false if you want to continue the process, or it is true  if you want to request to suspend the process.
     */
    boolean progress(final String argProgressMessage);
}
