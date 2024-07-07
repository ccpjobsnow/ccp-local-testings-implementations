package com.ccp.local.testings.implementations;

import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.dependency.injection.CcpInstanceProvider;
import com.ccp.implementations.db.bulk.elasticsearch.CcpElasticSerchDbBulk;
import com.ccp.implementations.db.crud.elasticsearch.CcpElasticSearchCrud;
import com.ccp.implementations.db.query.elasticsearch.CcpElasticSearchQueryExecutor;
import com.ccp.implementations.db.utils.elasticsearch.CcpElasticSearchDbRequest;
import com.ccp.implementations.email.sendgrid.CcpSendGridEmailSender;
import com.ccp.implementations.file.bucket.gcp.CcpGcpFileBucket;
import com.ccp.implementations.http.apache.mime.CcpApacheMimeHttp;
import com.ccp.implementations.instant.messenger.telegram.CcpTelegramInstantMessenger;
import com.ccp.implementations.json.gson.CcpGsonJsonHandler;

public enum CcpLocalInstances implements CcpInstanceProvider<Object>{
	email {
		public Object getInstance() {
			LocalEmailSender localEmailSender = new LocalEmailSender();
			return localEmailSender;
		}
	},
	cache {
		public Object getInstance() {
			LocalCache localCache = new LocalCache();
			return localCache;
		}
	}, bucket {
		public Object getInstance() {
			return new LocalBucket();
		}
	}, mensageriaSender {
		public Object getInstance() {
			LocalMensageriaSender localMensageriaSender = new LocalMensageriaSender();
			return localMensageriaSender;
		}
	};

	abstract public Object getInstance();
	
	public CcpInstanceProvider<Object> getLocalImplementation(CcpInstanceProvider<?> businessInstanceProvider) {
		CcpDependencyInjection.loadAllDependencies(businessInstanceProvider);
		return this;
	}
	
	private CcpLocalInstances() {
		CcpDependencyInjection.loadAllDependencies(new CcpElasticSearchQueryExecutor(),
				new CcpTelegramInstantMessenger(), new CcpElasticSearchDbRequest(),
				new CcpSendGridEmailSender(), new CcpElasticSerchDbBulk(), new CcpElasticSearchCrud(),
				new CcpGsonJsonHandler(), new CcpApacheMimeHttp(), new CcpGcpFileBucket()

		);

	}
}
