# Obvious Actor Patterns (That maybe aren't so obvious)

To say that I'm a firm believer in the actor model of concurrency would be an understatement. The
actor model has some very specific properties that mean that it may not be ideal for every use case,
but it does a pretty good job of making concurrency and thread-safety of state sane for The Averavge
Human™.

This is a collection of patterns for using actors in Scala code that I learned. To most of the
committers on the Akka project I expect these patterns are obvious. But to the rest of the developer
community just trying to kludge their way toward a working application they may not be. I'm not an
Akka expert by any means, but I've got a few tricks that weren't obvious to some friends and
coworkers. So, I thought I'd list them here.

## Table of Contents

### Actor-to-Actor Patterns

1. **The Send-and-Receive.** Sending a message to an actor then receiving a response back in another
  message.
2. **The Hot Potato.** Forwarding a message to another actor and handling it appropriately.
3. **The Pass Around.** Doing clever things with the sender ref.
4. **The Stand in Line.** Doing clever things with *multiple* sender refs.
5. **The Bossman.** He's always telling me what to do... gah...

#### Future-to-Actor patterns

1. **The (A)Wait Around.** The noblest of all ways to sit around and do nothing.
2. **Map to the Future.** Clever ways to not wait for the inevitable.

# About Me

My name is Matt Farmer. I'm a software engineer based out of Atlanta, GA, who has been doing Scala
since 2011. By day, I'm helping make a difference for data scientists with
[Domino Data Lab](http://dominodatalab.com). By night, I consult under the banner Crazy Goat
Creative and contribute to open source projects like [The Lift Framework](http://liftweb.net).
I occasionally [write things](http://farmdawgnation.com).
