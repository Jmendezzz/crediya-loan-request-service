package co.com.crediya.webclient.exceptions;

public class WebClientException extends RuntimeException {

    public WebClientException(){
        super("Error interno en la comunicacion de servicios");
    }
}
