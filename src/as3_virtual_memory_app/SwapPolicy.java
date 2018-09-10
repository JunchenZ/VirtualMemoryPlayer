package as3_virtual_memory_app;

public interface SwapPolicy {
	
	void pageAccessed(int page);
	int whichPageShouldBeEvicted();
}
