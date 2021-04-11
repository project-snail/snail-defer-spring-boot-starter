## snail-defer-starter 

使java支持defer和recover方法

开启defer操作

```xml
<dependency>
    <groupId>io.github.project-snail</groupId>
    <artifactId>snail-defer-starter</artifactId>
    <version>1.0</version>
</dependency>
```

```yaml
snail:
  defer:
    enable: true
```
另外可以在类或方法上标识 @Deferrable 即可让此作用域支持操作


```java
import static com.snail.defer.DeferUtil.defer;

@Deferrable
@Component
public class FooComponent {

    @Deferrable
    public Pair<Integer, String> handler() {

        DeferUtil.defer(
            () -> {
//                do something
            }
        );

        defer(
            () -> {
//                do something
            }
        );

        DeferUtil.recover(
            err -> Pair.of(1, "1")
        );

//                    zero err
        return Pair.of(1 / 0, "1");

    }

}
```