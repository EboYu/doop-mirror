package org.clyze.doop.input

/**
 * The input resolution mechanism.
 * Resolves inputFiles (given as strings) to a set of files.
 */
interface InputResolutionContext {

    /**
     * Checks whether transitive dependencies are supported.
     */
    boolean isTransitive()

    /**
     * Sets the support of transitive dependencies.
     */
    void setTransitive(boolean transitive)

    /**
     * Adds the given input for resolution.
     */
    void add(String input)

    /**
     * Adds the given list of inputFiles for resolution.
     */
    void add(List<String> inputs)

    /**
     * Sets the file that corresponds to the given input.
     * @param input - the input as a string
     * @param file - the file it corresponds to
     */
    void set(String input, File file)

    /**
     * Sets the files that correspond to the given input.
     * @param input - the input as a string
     * @param files - the files it corresponds to
     */
    void set(String input, List<File> files)

    /**
     * Gets the file(s) that correspond to the given input.
     * @param input - the input as a string
     * @return the corresponding file(s)
     */
    Set<File> get(String input)

    /**
     * Resolves the inputFiles to their corresponding files.
     * If an input is unresolved --it has not file(s)-- an exception is thrown.
     */
    void resolve()

    /**
     * Gets the set of files that correspond to the inputFiles of this context.
     * If an input is found to be unresolved --it has no file(s)-- an exception is thrown.
     */
    List<File> getAll()

    /**
     * Returns the inputFiles of this context.
     */
    List<String> inputs()
}
