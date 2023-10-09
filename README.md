# concurrency-issues
동시성 이슈 해결 관련 학습

### 전제  
재고 감소 로직을 만들어 여러 쓰레드에서 동시에 실행했을때, race condition이 발생한다.  
이를 해결하기 위한 방법을 학습.


#### synchronized  
  
@Transactional은 내부적으로 클래스를 새로 만들어 메서드를 실행한다.  
synchronized을 붙여도 이 과정에서 트랜잭션이 끝나기 전에 다른 쓰레드가 메서드를 실행 가능 하기에, 주석처리하고 테스트.  
- synchronized는 실행중인 프로세스 내에서만 유효하기 때문에, 실제 운영중인 여러대의 서버에서는 race condition이 발생하게 된다.


#### Mysql을 이용한 방법  
Pessimistic Lock  
실제로 데이터에 Lock을 걸어서 정합성을 맞추는 방법이다.   
exclusive lock을 걸게 되면 다른 트랜잭션에서는 lock이 해제되기 전에 데이터를 가져갈 수 없다.  
데드락이 걸릴 수 있기 때문에 주의해야한다.  
  
Optimistic Lock  
실제로 Lock을 이용하지 않고 버전을 이용함으로써 정합성을 맞추는 방법.  
먼저 데이터를 읽은 후에 update를 수행할 때 현재 내가 읽은 버전이 맞는지 확인하며 업데이트를 한다.  
내가 읽은 버전에서 수정사항이 생겼을 경우에는 application에서 다시 읽은 후에 작업을 수행해야 한다.  

Named Lock    
이름을 가진 metadata locking.    
이름을 가진 lock을 획득한 후 해제할때까지 다른 세션은 이 lock을 획득할 수 없도록 한다.  
주의할점으로는 Transaction이 종료될 때 lock이 자동으로 해제되지 않는다.  
별도의 명령어로 해제를 수행해주거나 선점시간이 끝나야 해제된다.  
