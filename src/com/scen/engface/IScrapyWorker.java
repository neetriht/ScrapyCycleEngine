package com.scen.engface;

public interface IScrapyWorker {
    public int startToWork(String stock_code, String threadid);

    public IScrapyWorker newInstance();
}
