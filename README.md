# metabox

Clojure doesn't allow metadata on primitives. This tiny library provides a
simple way to box a primitive and add metadata to it.

## Usage

Here is how you use it.

```clj
(require '[metabox.core :refer (box val)])
(def critical-density (box 0.692 {:uncertainty 0.01}))
(val critical-density) ; 0.692
(meta critical-density) ; {:uncertainty 0.01}
```

(Note that I'm using the var, `critical-density`, as a convenience. I want to
attach the metadata to the value, not to the var.)

This library provides a simple (perhaps the simplest*) way to box a primitive
that is also metadata friendly. Clojure metadata works with classes descending
from `clojure.lang.IObj`. So, this code provides:

  1. a custom Java class called `MetaBox` that implements `clojure.lang.IObj`.
  2. two Clojure functions to work with metaboxes: `metabox/box` and
     `metabox/val`.

You can also use the usual Clojure metadata functions, such as `meta` and
`with-meta`.

\* This is a matter of perspective. If you find something simpler for you, let
me know, or share on this Stack Overflow question [Simplest
possible Clojure object that can accept a primitive and metadata?][1]

[1]: http://stackoverflow.com/questions/20724219/simplest-possible-clojure-object-that-can-accept-a-primitive-and-metadata

## Alternatives

There are alternatives. For example, you might use a vector:

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

## Motivation

I created this because I want to attach metadata to byte arrays. Clojure
doesn't allow that because arrays are primitives.

Here is how this works with `metabox`:

```clj
(require '[metabox.core :refer (box val)])
(def many-bytes (box (byte-array 100) {:charset "UTF-8"}))
(val many-bytes) ; #<byte[] [B@4b4340e2>
(meta many-bytes) ; {:charset "UTF-8"}
```

I consider some alternatives, including variations of the one mentioned above.
I also looked at [ArrayUtils/toObject][AU] from [Apache Commons lang][ACL].
But I don't want to [copy the byte array][copy-byte-array]; I simply want to
box it. You could do something similarly unsatisying with Clojure's
[make-array] function.

[AU]: https://commons.apache.org/proper/commons-lang/javadocs/api-3.1/org/apache/commons/lang3/ArrayUtils.html#toObject(byte[])
[ACL]: http://commons.apache.org/proper/commons-lang/
[copy-byte-array]: https://github.com/apache/commons-lang/blob/trunk/src/main/java/org/apache/commons/lang3/ArrayUtils.java#L3228
[make-array]: http://clojuredocs.org/clojure_core/clojure.core/make-array

## License

Copyright 2013 Bluemont Labs LLC

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
