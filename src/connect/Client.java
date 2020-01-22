package connect;

import java.io.IOException;

public interface Client {

	void start();

	void close() throws IOException;

	public void set(String path, Double val);

}
