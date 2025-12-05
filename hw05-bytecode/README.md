# Автоматическое логирование
ДЗ к занятию 10 (Байт код, class-loader, инструментация, asm)

Цель: Понять как реализуется AOP, какие для этого есть технические средства.

## Задание:
Разработайте такой функционал:
- метод класса можно пометить самодельной аннотацией `@Log`, например, так:
  ```java
  class TestLogging implements TestLoggingInterface {
      @Log
      public void calculation(int param) {};
  }
  ```
- при вызове этого метода "автомагически" в консоль должны логироваться значения параметров, например так:
  ```java
  class Demo {
      public void action() {
          new TestLogging().calculation(6);
      }
  }
  ```
В консоле дожно быть:
```shell
executed method: calculation, param: 6
```
Обратите внимание: явного вызова логирования быть не должно.
- учтите, что аннотацию можно поставить, например, на такие методы:
  ```java
  public void calculation(int param1)
  public void calculation(int param1, int param2)
  public void calculation(int param1, int param2, String param3)
  ```
