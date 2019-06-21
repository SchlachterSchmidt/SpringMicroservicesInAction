package SpringMicroservicesInAction.OrganizationService.hystrix

import SpringMicroservicesInAction.OrganizationService.utils.UserContextHolder
import com.netflix.hystrix.HystrixThreadPoolKey
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariable
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableLifecycle
import com.netflix.hystrix.strategy.properties.HystrixProperty
import java.util.concurrent.BlockingQueue
import java.util.concurrent.Callable
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class ThreadLocalAwareStrategy(val existingConcurrencyStrategy: HystrixConcurrencyStrategy?) : HystrixConcurrencyStrategy() {

    override fun getBlockingQueue(maxQueueSize: Int): BlockingQueue<Runnable> {
        return when (existingConcurrencyStrategy) {
            null -> super.getBlockingQueue(maxQueueSize)
            else -> existingConcurrencyStrategy.getBlockingQueue(maxQueueSize)
        }
    }

    override fun <T : Any?> getRequestVariable(rv: HystrixRequestVariableLifecycle<T>?): HystrixRequestVariable<T> {
        return when (existingConcurrencyStrategy) {
            null -> super.getRequestVariable(rv)
            else -> existingConcurrencyStrategy.getRequestVariable(rv)
        }
    }

    override fun getThreadPool(threadPoolKey: HystrixThreadPoolKey?,
                               corePoolSize: HystrixProperty<Int>?,
                               maximumPoolSize: HystrixProperty<Int>?,
                               keepAliveTime: HystrixProperty<Int>?, unit: TimeUnit?,
                               workQueue: BlockingQueue<Runnable>?): ThreadPoolExecutor {
        return when (existingConcurrencyStrategy) {
            null -> super.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue)
            else -> existingConcurrencyStrategy.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue)
        }
    }

    override fun <T : Any> wrapCallable(callable: Callable<T>): Callable<T> {
        return when (existingConcurrencyStrategy) {
            null -> super.wrapCallable(DelegatingUserContextCallable(callable, UserContextHolder.getContext()))
            else -> existingConcurrencyStrategy.wrapCallable(DelegatingUserContextCallable(callable, UserContextHolder.getContext()))
        }
    }
}