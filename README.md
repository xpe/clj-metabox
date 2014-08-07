# metabox

Clojure doesn't allow metadata on primitives. This tiny library provides a
simple way to box a primitive and add metadata to it.

# Summary

This library provides a simple* way to box a primitive that is also metadata friendly.

  * To box a value, use `metabox/box`.
  * To get the value, use `deref` or `@`.
  * To get the metadata, use `meta`.
  * To attach new metadata, use `with-meta`.

\* This is a matter of perspective. If you find something simpler for you, let
me know, or share on this Stack Overflow question: ["Simplest
possible Clojure object that can accept a primitive and metadata?"][stack-o].

[stack-o]: http://stackoverflow.com/questions/20724219/simplest-possible-clojure-object-that-can-accept-a-primitive-and-metadata

## Example

Now for an example that is universally applicable:

```clj
(require '[metabox.core :refer (box)])
(def critical-density (box 0.692 {:uncertainty 0.01}))
@critical-density ; 0.692
(meta critical-density) ; {:uncertainty 0.01}
```

(Note that I'm using the var, `critical-density`, as a convenience. I want to
attach the metadata to the value, not to the var.)

## Implementation

Clojure metadata works with classes descending from `clojure.lang.IObj`. So,
this code provides a `MetaBox` class that implements `clojure.lang.IObj`.

`MetaBox` also implements `clojure.lang.IDeref` because that provides `deref`, a
convenient way to provide a way to get the value out of the box.

## Alternatives

There are other ways to achieve boxing; for example, you might use a vector:

```clj
(def speed-of-light (with-meta [299792458] {:units "m/s"}))
```

There are at least two drawbacks with the vector approach:

  * Conceptual: You want boxing only, but a vector is not the right way to do
    it, conceptually, since it can hold more than one thing.
  * Implementation: A vector is not the simplest implementation that will
    work. If you look at how vectors are implemented in Java, you will see
    that `PersistentVector` implements `clojure.lang.IObj` but also adds other
    functionality not needed for boxing.

No matter how you do it, Howard, I hope that [boxing has been good for you][boxing].

[boxing]: https://www.youtube.com/watch?v=DlT4aFDZ-AM

## Motivation

I created this because I want to attach metadata to byte arrays. Clojure
doesn't allow that because arrays are primitives.

Here is how this works with `metabox`:

```clj
(require '[metabox.core :refer (box)])
(def many-bytes (box (byte-array 100) {:charset "UTF-8"}))
(deref many-bytes) ; #<byte[] [B@4b4340e2>
(meta many-bytes) ; {:charset "UTF-8"}
```

I considered some alternatives, such as wrapping with Clojure maps and
vectors, as mentiond above. I also looked at [ArrayUtils/toObject][AU] from
[Apache Commons lang][ACL]. But I don't want to [copy the byte array]; I
simply want to box it. You could do something similarly unsatisying with
Clojure's [make-array] function.

[AU]: https://commons.apache.org/proper/commons-lang/javadocs/api-3.1/org/apache/commons/lang3/ArrayUtils.html#toObject(byte[])
[ACL]: http://commons.apache.org/proper/commons-lang/
[copy the byte array]: https://github.com/apache/commons-lang/blob/trunk/src/main/java/org/apache/commons/lang3/ArrayUtils.java#L3228
[make-array]: http://clojuredocs.org/clojure_core/clojure.core/make-array

## License

Copyright 2013 Bluemont Labs LLC

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
