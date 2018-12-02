# Account Fetcher

## Requirements

* Should retrieve all the accounts with known email and phone numbers.

## Functional goals

* Retrieval should minimize total response time

## Out of scope

Even though we are emulating io-based/network services, we will not consider error handling.
The conversation about how and when to return partial results or how to handle unexpected failure is out of scope
 
## Details

### Services
* An id service that returns all known Account ids
  * Has consistent response time for 10 m/s
* An email service that returns a single email address for an account
  * Fails exactly 75% of the time (1 in 4)
  * Has consistent response of 10 m/s
* A phone service the returns a single email address for an account
  * Fails exactly 50% of the time (1 in 2)
  * Has consistent response time of 10 m/s
 
### Worst Case Performance

Synchronously requesting data from each service would require us to wait:

For 100 accounts: 10 + (100 * 10) + (100 * 10) = ~2010 m/s

### Best Case Synchronous Performance

We also know that the success rate of actually fetching a complete account
is exactly 25% * 50% or 12.5%. If we call both services for each account id, we will be doing unnecessary work 87.5%
of the time. We know for a fact that our email service has a much lower hit rate than our phone service, so only call
the phone services after determining the email service has returned a match, we save a considerable amount of waiting:

For 100 accounts: 10 + (100 * 10) + (25 * 10) = ~1260 m/s.

This is marked improvement, but we are still operating asynchronously.

### Best Case Asynchronous Performance

We can do much better if we contact services in parallel. We known that increasing the rate at which we request
data will not increase response time, so there is nothing stopping us for doing all requests in parallel.

If perfect parallelism (no serial work or comms overhead):

For 100 accounts: 10 + 10 + 10 = 30 m/s

Back of the napkin real-world:

95% is parallelizable, 5% is communication overhead or serial

Overhead is: 1260 * 5% = 63 m/s

Parallel is: 1260 * 95% * 10% = ~1197 m/s * 10% = ~11 m/s

A possible result: 63 m/s + 11 m/s = ~74 m/s

### Java implementation

My knowledge of Intel's TPB and Microsoft's TPL (two popular task-based parallelization libraries) made 
it relatively easy to leverage Java 8's new CompletableFutures API. Other paths I could have used might have
included a home spun thread pool with a blocking queue on either end to supply threads jobs and retrieve thread
results results. In the end, CFs where quicker to implement, and additionally I used the continuation features
to ease the detection of the completion of all fetching tasks.

### Results of the java implementation

* Brute-force (from individual tests): 10 + 1063 + 1076 = 2149 m/s
* Parallelized with previous observations (from test): 63 m/s

So 63 m/s meets with the expectations of our napkin estimate of 74 m/s





 
  
 