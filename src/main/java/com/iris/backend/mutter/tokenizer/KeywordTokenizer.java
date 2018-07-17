package com.iris.backend.mutter.tokenizer;

import com.rabidgremlin.mutters.core.Tokenizer;

public class KeywordTokenizer implements Tokenizer {

	@Override
	public String[] tokenize(String text) {
		return new String[] { text };
	}

}
