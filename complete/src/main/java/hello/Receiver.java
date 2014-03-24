package hello;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import reactor.event.Event;
import reactor.spring.annotation.Selector;

@Service
public class Receiver {

    @Autowired
    CountDownLatch latch;
    
    RestTemplate restTemplate = new RestTemplate();

    @Selector(value="jokes", reactor="@rootReactor")
    public void handleJokeEvent(Event<Integer> ev) {
    	System.out.println("Getting Chuck Joke");
        JokeResource jokeResource = restTemplate.getForObject("http://api.icndb.com/jokes/random", JokeResource.class);
        System.out.println("Joke " + ev.getData() + ": " + jokeResource.getValue().getJoke());
        latch.countDown();
    }

}