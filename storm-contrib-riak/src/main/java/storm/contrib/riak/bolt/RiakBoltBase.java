package storm.contrib.riak.bolt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.RiakFactory;
import com.basho.riak.client.RiakException;
import com.basho.riak.client.bucket.Bucket;
import com.basho.riak.client.http.RiakClient;

import org.apache.log4j.Logger;
import storm.contrib.riak.StormRiakObjectGrabber;

import java.util.Map;

public abstract class RiakBoltBase extends BaseRichBolt {
  /**
	 * 
	 */
	private static final long serialVersionUID = 3835435457758371258L;

static Logger LOG = Logger.getLogger(RiakBoltBase.class);

  // Bolt runtime objects
  @SuppressWarnings("rawtypes")
  protected Map map;
  protected TopologyContext topologyContext;
  protected OutputCollector outputCollector;

  // Constructor arguments
  protected String url;
  protected String bucketName;
  protected StormRiakObjectGrabber mapper;

  // Riak objects
  protected IRiakClient riakClient;
  protected Bucket bucket;

  public RiakBoltBase(String url, String bucketName, StormRiakObjectGrabber mapper) {
    this.url = url;
    this.bucketName = bucketName;
    this.mapper = mapper;
  }

  @Override
  public void prepare(@SuppressWarnings("rawtypes") Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
    // Save all the objects from storm
    this.map = map;
    this.topologyContext = topologyContext;
    this.outputCollector = outputCollector;

    // Attempt to open a Riak connection
    try {
      RiakClient uri = new RiakClient(this.url);
      this.riakClient = RiakFactory.httpClient(uri);

      // If we need to authenticate do it
//      if (uri.getUsername() != null) {
//        this.db.authenticate(uri.getUsername(), uri.getPassword());
//      }

      // Grab the collection from the uri
      this.bucket = this.riakClient.fetchBucket(this.bucketName).execute();
    } catch (RiakException e) {
    	throw new RuntimeException(e);
	}
  }

  @Override
  public abstract void execute(Tuple tuple);

  /**
   * Lets you handle any additional emission you wish to do
   *
   * @param tuple
   */
  public abstract void afterExecuteTuple(Tuple tuple);

  @Override
  public abstract void cleanup();

  @Override
  public abstract void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer);
}
