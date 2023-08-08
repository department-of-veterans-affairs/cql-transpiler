// package gov.va.sparkcql.repository;

// import java.util.List;

// import org.apache.spark.sql.Encoder;
// import org.apache.spark.sql.Encoders;

// import org.apache.spark.sql.SparkSession;

// import gov.va.sparkcql.common.configuration.Configuration;
// import gov.va.sparkcql.common.log.Log;
// import gov.va.sparkcql.model.CqlSource;
// import gov.va.sparkcql.common.configuration.ComponentFactory;
// import gov.va.sparkcql.common.configuration.ConfigKey;

// public abstract class BaseSparkRepository<K, T> implements Repository<K, T> {

//     protected SparkSession spark;
//     protected String tableName;

//     public BaseSparkRepository() {
//         this(Configuration.getGlobalConfig());
//     }

//     public BaseSparkRepository(Configuration cfg) {
//         this(cfg.read(ConfigKey.SPARKCQL_DEFAULT_TABLEBINDING, "${type}"));
//     }

//     public BaseSparkRepository(String tablebinding) {
//         this.spark = ComponentFactory.createSpark();
//         this.tableName = tablebinding;
//         tableName = tableName.replace("${type}", getEntityClass().getSimpleName()).toLowerCase();
//         tableName = tableName.replace("${TYPE}", getEntityClass().getSimpleName()).toUpperCase();
//         tableName = tableName.replace("${Type}", getEntityClass().getSimpleName());
//     }

//     protected abstract Class<T> getEntityClass();

//     protected abstract String getEntityKeyColumn();

//     public Encoder<T> getEncoder() {
//         return Encoders.bean(getEntityClass());
//     }

//     @Override
//     public T findOne(K key) {
//         var ds = spark.table(this.tableName).as(getEncoder());
//         return ds.filter(ds.col(getEntityKeyColumn()).equalTo(key)).first();
//     }

//     @Override
//     public List<T> findMany(List<K> keys) {
//         var ds = spark.table(this.tableName).as(getEncoder());
//         return ds.filter(ds.col(getEntityKeyColumn()).contains(keys)).collectAsList();
//     }

//     @Override
//     public Boolean exists(K key) {
//         var ds = spark.table(this.tableName).as(getEncoder());
//         return !ds.filter(ds.col(getEntityKeyColumn()).equalTo(key)).isEmpty();
//     }
// }