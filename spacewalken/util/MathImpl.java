package spacewalken.util;

/**
 * Created and written with IntelliJ IDEA.
 * Module of: Minceraft
 * User: brennanforrest
 * Date of creation: 7/11/13, at 12:25 AM.
 */
class MathImpl
{
    /**
     * Calculates the <i>n</i>th Fibonacci number
     *
     * @param n
     *
     * @return
     */
    public static int fib(int n)
    {
        int num1 = 0, num2 = 0, fib = 0;
        for (int i = 0; i < n; i++)
        {
            fib = num1 + num2;
            num1 = num2;
            num2 = fib;
        }
        return fib;
    }

    /** @return Tau, or PI * 2. */
    public static double TAU()
    {
        return Math.PI * 2;
    }

    /**
     * Averages all doubles in a double array.
     *
     * @param doubles The numbers to be averaged
     *
     * @return
     */
    public static double avg(double... doubles)
    {
        double sum = 0;
        for (double i : doubles)
        {
            sum += i;
        }
        return Double.parseDouble(sum / doubles.length + "." + sum % doubles.length);
    }

    /**
     * Divides two integers and returns the product and remainder.
     *
     * @param i
     * @param j
     *
     * @return a number with the appropriate remainder.
     */
    public static float div(int i, int j)
    {
        return Float.parseFloat((i / j) + "." + (i % j));
    }
}
