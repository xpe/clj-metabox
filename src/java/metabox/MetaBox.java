package metabox;

import clojure.lang.IDeref;
import clojure.lang.IObj;
import clojure.lang.IPersistentMap;

public class MetaBox implements IDeref, IObj {

  final Object _val;
  final IPersistentMap _meta;

  public MetaBox() {
    _val = null;
    _meta = null;
  }

  public MetaBox(Object val) {
    _val = val;
    _meta = null;
  }

  public MetaBox(Object val, IPersistentMap meta) {
    _val = val;
    _meta = meta;
  }

  final public Object deref() {
    return _val;
  }

  final public IPersistentMap meta() {
    return _meta;
  }

  public MetaBox withMeta(IPersistentMap meta) {
    return new MetaBox(_val, meta);
  }

  public String toString() {
    if (_val != null)
      return _val.toString();
    else
      return super.toString();
  }

}

