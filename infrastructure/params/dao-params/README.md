# QueryParams
For SQL, HQL for packaging, do not need to write their own SQL, automatic generate corresponding entities DaoQueryParam, simply set can achieve the SQL generated

Think shouldn't waste time on the red side where clause (also let the beginner friends friends to reduce the difficulty of SQL), I think that can be directly used as entity entities associated with the table. Then integrate JFianl or Hibernate, then development and perfect, as the development of the individual, also hope to have like-minded together to complete.

Below is the specific DaoParam automatically generated (As a database mapping entity, under test )
```java

   /**
    * mn_member Request parameters wrapper class 
    * Thu Oct 15 18:11:09 CST 2015 ZaoSheng
    */ 
//@Table(name = "mn_member")
//@Entity
public class MnMemberDaoParams extends Where{
    public MnMemberDaoParams() {
        alias = "mn_member";
                where();
    }
    public enum Field {
        mid("mid"),deviceId("device_id"),deviceType("device_type"),version("version"),ip("IP");
        private String column;

        private Field(String column) {
            this.column = column;
        }
        public String getColumn() {
            return column;

        }
        public static String getSelect()
        {
            return getSelect("d");
        }
        public static String getSelect(String prefix)
        {
            StringBuilder sb = new StringBuilder();
            for ( Field field : Field.values()) {
                sb.append(String.format(" %s.`%s` %s, ", prefix, field.getColumn(), field.name()));
            }
            sb.deleteCharAt(sb.length() - 2);
            return sb.toString();
        }
    }
    public MnMemberDaoParams setmid(Long mid){
        and(Field.mid.name(), mid, alias);
        return this;
    }
    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name = "mid")
    public Long getmid(){
        return (Long) attrs.get(Field.mid.name());
    }
    public MnMemberDaoParams setmidBetween(Long[] mid) {
        and(Field.mid.name(), mid, Restriction.BW, alias);
        return this;
    }
    public MnMemberDaoParams setmidNE(Long mid) {
        and(Field.mid.name(), mid, Restriction.NE, alias);
        return this;
    }
    public MnMemberDaoParams setmidGE(Long mid) {
        and(Field.mid.name(), mid, Restriction.GE, alias);
        return this;
    }
    public MnMemberDaoParams setmidGT(Long mid) {
        and(Field.mid.name(), mid, Restriction.GT, alias);
        return this;
    }
    public MnMemberDaoParams setmidIn(List<Object> mid) {
        and(Field.mid.name(), mid, Restriction.IN, alias);
        return this;
    }
    public MnMemberDaoParams setmidNIn(List<Object> mid) {
        and(Field.mid.name(), mid, Restriction.NIN, alias);
        return this;
    }
    public MnMemberDaoParams setmidNull() {
        and(Field.mid.name(), null, Restriction.NUL, alias);
        return this;
    }
    public MnMemberDaoParams setmidNotNull() {
        and(Field.mid.name(), null, Restriction.NNUL, alias);
        return this;
    }
    public MnMemberDaoParams setdeviceType(Integer deviceType){
        and(Field.deviceType.name(), deviceType, alias);
        return this;
    }
public MnMemberDaoParams setdeviceId(String deviceId){
        and(Field.deviceId.name(), deviceId, alias);
        return this;
    }
    //@Column(name = "device_id")
    public String getdeviceId(){
        return (String) attrs.get(Field.deviceId.name());
    }
    public MnMemberDaoParams setdeviceIdLLike(String deviceId) {
        and(Field.deviceId.name(), deviceId, Restriction.LLIKE, alias);
        return this;
    }
    public MnMemberDaoParams setdeviceIdRLike(String deviceId) {
        and(Field.deviceId.name(), deviceId, Restriction.RLIKE, alias);
        return this;
    }
    public MnMemberDaoParams setdeviceIdLike(String deviceId) {
        and(Field.deviceId.name(), deviceId, Restriction.LIKE, alias);
        return this;
    }
    public MnMemberDaoParams setdeviceIdIn(List<Object> deviceId) {
        and(Field.deviceId.name(), deviceId, Restriction.IN, alias);
        return this;
    }
    public MnMemberDaoParams setdeviceIdNIn(List<Object> deviceId) {
        and(Field.deviceId.name(), deviceId, Restriction.NIN, alias);
        return this;
    }
    public MnMemberDaoParams setdeviceIdNull() {
        and(Field.deviceId.name(), null, Restriction.NULL, alias);
        return this;
    }
    public MnMemberDaoParams setdeviceIdNotNull() {
        and(Field.deviceId.name(), null, Restriction.NOTNULL, alias);
        return this;
    }
    public MnMemberDaoParams setdeviceType(Integer deviceType){
        and(Field.deviceType.name(), deviceType, alias);
        return this;
    }
    //@Column(name = "device_type")
    public Integer getdeviceType(){
        return (Integer) attrs.get(Field.deviceType.name());
    }
    public MnMemberDaoParams setdeviceTypeBetween(Integer[] deviceType) {
        and(Field.deviceType.name(), deviceType, Restriction.BW, alias);
        return this;
    }
    public MnMemberDaoParams setdeviceTypeNE(Integer deviceType) {
        and(Field.deviceType.name(), deviceType, Restriction.NE, alias);
        return this;
    }
    public MnMemberDaoParams setdeviceTypeGE(Integer deviceType) {
        and(Field.deviceType.name(), deviceType, Restriction.GE, alias);
        return this;
    }
    public MnMemberDaoParams setdeviceTypeGT(Integer deviceType) {
        and(Field.deviceType.name(), deviceType, Restriction.GT, alias);
        return this;
    }
    public MnMemberDaoParams setdeviceTypeIn(List<Object> deviceType) {
        and(Field.deviceType.name(), deviceType, Restriction.IN, alias);
        return this;
    }
    public MnMemberDaoParams setdeviceTypeNIn(List<Object> deviceType) {
        and(Field.deviceType.name(), deviceType, Restriction.NIN, alias);
        return this;
    }
    public MnMemberDaoParams setdeviceTypeNull() {
        and(Field.deviceType.name(), null, Restriction.NUL, alias);
        return this;
    }
    public MnMemberDaoParams setdeviceTypeNotNull() {
        and(Field.deviceType.name(), null, Restriction.NNUL, alias);
        return this;
    }
    public MnMemberDaoParams setdeviceToken(String deviceToken){
        and(Field.deviceToken.name(), deviceToken, alias);
        return this;
    }
    //@Column(name = "version")
    public String getversion(){
        return (String) attrs.get(Field.version.name());
    }
    public MnMemberDaoParams setversionLLike(String version) {
        and(Field.version.name(), version, Restriction.LLK, alias);
        return this;
    }
    public MnMemberDaoParams setversionRLike(String version) {
        and(Field.version.name(), version, Restriction.RLK, alias);
        return this;
    }
    public MnMemberDaoParams setversionLike(String version) {
        and(Field.version.name(), version, Restriction.LK, alias);
        return this;
    }
    public MnMemberDaoParams setversionIn(List<Object> version) {
        and(Field.version.name(), version, Restriction.IN, alias);
        return this;
    }
    public MnMemberDaoParams setversionNIn(List<Object> version) {
        and(Field.version.name(), version, Restriction.NIN, alias);
        return this;
    }
    public MnMemberDaoParams setversionNull() {
        and(Field.version.name(), null, Restriction.NUL, alias);
        return this;
    }
    public MnMemberDaoParams setversionNotNull() {
        and(Field.version.name(), null, Restriction.NNUL, alias);
        return this;
    }
    //@Column(name = "IP")
    public String getip(){
        return (String) attrs.get(Field.ip.name());
    }
    public MnMemberDaoParams setipLLike(String ip) {
        and(Field.ip.name(), ip, Restriction.LLK, alias);
        return this;
    }
    public MnMemberDaoParams setipRLike(String ip) {
        and(Field.ip.name(), ip, Restriction.RLK, alias);
        return this;
    }
    public MnMemberDaoParams setipLike(String ip) {
        and(Field.ip.name(), ip, Restriction.LK, alias);
        return this;
    }
    public MnMemberDaoParams setipIn(List<Object> ip) {
        and(Field.ip.name(), ip, Restriction.IN, alias);
        return this;
    }
    public MnMemberDaoParams setipNIn(List<Object> ip) {
        and(Field.ip.name(), ip, Restriction.NIN, alias);
        return this;
    }
    public MnMemberDaoParams setipNull() {
        and(Field.ip.name(), null, Restriction.NUL, alias);
        return this;
    }
    public MnMemberDaoParams setipNotNull() {
        and(Field.ip.name(), null, Restriction.NNUL, alias);
        return this;
    }
}
//Followed by the corresponding simple test, the where clause 

    public static void main(String[] args) {
        MemberDaoParams params1 = new MemberDaoParams();
        params1.setdeviceId("deviceId1").setversion("2.0").or(GoodsOwerDaoParams.Field.cityName.name(), "XiaMen", "mn_goods_ower");
        System.out.println("----------------Here is the key, the value form -------------");
        //Here is the key, the value form 
        System.out.println(params1.builderAttrs().getSqlString());
        for (String p : params1.getAttrs().keySet()) {
            System.out.println(String.format("p:%s_____value:%s", p, params1.getAttrs().get(p).toString()));

        }

        //Not reset the internal storage, there needs to be manually reset. Front of or behind will lead to the key and the value together (usually don't like me to write both form... this is a test, but the next is to repair) 
        params1.sql.setLength(0);
        System.out.println("----------------This is '?' Instead of the form -------------");
        //This is '?' Instead of the form 
        System.out.println(params1.builderParas().getSqlString());
        int i = 0;
        for(Object p : params1.getParas())  {
            System.out.println(String.format("The %s of ？to value：%s", ++i, p.toString()));

        }
    }
```

###The output ：
```html
----------------Here is the key, the value form-------------
 mn_member.deviceId= :deviceId  or mn_goods_ower.cityName= :cityName and mn_member.version= :version 
p:cityName_____value:XiaMen
p:deviceId_____value:deviceId1
p:version_____value:2.0
----------------This is '?' Instead of the form-------------
 mn_member.deviceId= ?  or mn_goods_ower.cityName= ? and mn_member.version= ? 
P1 of ？to value：deviceId1
P2 of ？to value：XiaMen
P3 of ？to value：2.0

```




	
