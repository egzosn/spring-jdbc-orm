# sqlFilter
The rapid development of plug-in
According to certain rules to generate SQL or HQL 
### page test
```html
<html>
  <head>
    <title></title>
  </head>
  <style type="text/css">
      .top{

          width: 100%;
          height: 50px;
          text-align: center;
      }
      .content{
          border: 1px solid #666;
          width: 100%;
          height: 600px;
      }
  </style>
  <body>
<div class="top">Dynamically generated sql</div>
 <div class="content">

        <div class="column">
            <form action="/testServlet" method="post">
            name(and ; right like):<input name="Q^t#name^!|^RLK"/><br/>
            age(and ; >=):<input name="Q^t#age^!|^GE^i"/><br/>
            sex(or ; =):<input name="Q^t#sex^|^EQ"/><br/>
                <input type="submit" value="generated">
            </form>
        </div>

 </div>
  </body>
</html>


```




###The results

```html
------? Instead of the form ------
sql: from test t where t.age>= ? or t.sex= ? and t.name like ?
P1:15P2:maleP3:Minna%
------The key, the value form ------
sql:from test t where t.age>= :age or t.sex= :sex and t.name like :name
key:sex; value:male
key:name; value:Minna%
key:age; value:15
```
