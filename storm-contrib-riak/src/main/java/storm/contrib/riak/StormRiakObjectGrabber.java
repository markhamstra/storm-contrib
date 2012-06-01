package storm.contrib.riak;

import backtype.storm.tuple.Tuple;
import com.basho.riak.client.IRiakObject;

import java.io.Serializable;

public abstract class StormRiakObjectGrabber implements Serializable {

	private static final long serialVersionUID = 3951127043177621286L;

	public abstract IRiakObject map(IRiakObject object, Tuple tuple);
}
