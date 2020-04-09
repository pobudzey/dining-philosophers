import java.util.Arrays;

/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */
	private final int numberOfPhilosophers;
	private enum States {THINKING, EATING, HUNGRY}
	private States[] state;

	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		numberOfPhilosophers = piNumberOfPhilosophers;
		state = new States[numberOfPhilosophers];
		// Initially, all philosophers are thinking
		Arrays.fill(state, States.THINKING);
	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	public synchronized void pickUp(final int piTID) throws InterruptedException
	{
		// Since BaseThread ids start at 1, we need to subtract 1 to get the index of the philosopher
		final int i = piTID - 1;

		state[i] = States.HUNGRY;
		while (!canEat(i))
		{
			wait();
		}
		state[i] = States.EATING;
	}

	/**
	 * When a given philosopher's done eating, they put the chopsticks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		// Since BaseThread ids start at 1, we need to subtract 1 to get the index of the philosopher
		final int i = piTID - 1;

		state[i] = States.THINKING;
		notifyAll();
	}

	/**
	 * Only one philosopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk()
	{
		// ...
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{
		// ...
	}

	private synchronized int leftNeighbor(final int i)
	{
		return (i + numberOfPhilosophers - 1) % numberOfPhilosophers;
	}

	private synchronized int rightNeighbor(final int i)
	{
		return (i + 1) % numberOfPhilosophers;
	}

	private synchronized boolean canEat(final int i)
	{
		return state[i] == States.HUNGRY
				&& state[leftNeighbor(i)] != States.EATING
				&& state[rightNeighbor(i)] != States.EATING;
	}
}

// EOF
