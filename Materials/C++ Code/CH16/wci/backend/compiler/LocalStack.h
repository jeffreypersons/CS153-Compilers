/**
 * <h1>LocalStack</h1>
 *
 * <p>Maintain a method's local runtime stack.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#ifndef LOCALSTACK_H_
#define LOCALSTACK_H_

namespace wci { namespace backend { namespace compiler {

class LocalStack
{
public:
    /**
     * Constructor
     */
    LocalStack();

    /**
     * Getter
     * @return the current stack size.
     */
    int get_size() const;

    /**
     * Increase the stack size by a given amount.
     * @param amount the amount to increase.
     */
    void increase(const int amount);

    /**
     * Decrease the stack size by a given amount.
     * @param amount the amount to decrease.
     */
    void decrease(const int amount);

    /**
     * Increase and decrease the stack size by the same amount.
     * @param amount the amount to increase and decrease.
     */
    void use(const int amount);

    /**
     * Increase and decrease the stack size by the different amounts.
     * @param amountIncrease the amount to increase.
     * @param amountDecrease the amount to decrease.
     */
    void use(const int amount_increase, const int amount_decrease);

    /**
     * Return the maximum attained stack size.
     * @return the maximum size.
     */
    int capacity() const;

private:
    int size;      // current stack size
    int max_size;  // maximum attained stack size
};

}}}  // namespace wci::backend::compiler

#endif /* LOCALSTACK_H_ */
