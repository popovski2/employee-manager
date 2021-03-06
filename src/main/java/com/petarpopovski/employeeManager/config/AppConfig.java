/*
package com.petarpopovski.employeeManager.config;

import org.redisson.Redisson;
import org.redisson.api.MapOptions;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.MapWriter;
import org.redisson.config.Config;


import com.petarpopovski.employeeManager.model.Employee;
import com.petarpopovski.employeeManager.repository.EmployeeRepo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Configuration
public class AppConfig implements InitializingBean {

    private final String CACHE_NAME="test-cache";

    private RedissonClient redissonClient;

    @Autowired
    private EmployeeRepo myRepository;

    @Bean
    public RMapCache<Long, Employee> employeeRMapCache() {
        final RMapCache<Long, Employee> employeeRMapCache = redissonClient.getMapCache(CACHE_NAME, MapOptions.<Long, Employee>defaults()
                .writer(getMapWriter())
                .writeMode(MapOptions.WriteMode.WRITE_THROUGH));
        return employeeRMapCache;
    }

    @Bean
    public RedissonClient redissonClient() {

        final Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379");
        return Redisson.create(config);
    }

    private MapWriter<Long, Employee> getMapWriter() {
        return new MapWriter<Long, Employee>() {

            @Override
            public void write(final Map<Long, Employee> map) {

                map.forEach( (k, v) -> {
                    myRepository.save(v);
                });
            }

            @Override
            public void delete(Collection<Long> keys) {
                keys.stream().forEach(e -> {
                    myRepository.deleteEmployeeById(e.longValue());
                });
            }
        };
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        final Config config = new Config();
        config.useMasterSlaveServers().setMasterAddress("redis://vm-minikube:30010")
                .setSlaveAddresses(Collections.singleton("redis://vm-minikube:30011"));
        this.redissonClient = Redisson.create(config);
    }
}*/
