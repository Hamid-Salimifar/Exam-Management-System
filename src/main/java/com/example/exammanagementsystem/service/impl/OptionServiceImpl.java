package com.example.exammanagementsystem.service.impl;

import com.example.exammanagementsystem.model.Option;
import com.example.exammanagementsystem.repository.OptionRepository;
import com.example.exammanagementsystem.service.OptionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tools.jackson.core.ObjectReadContext;

@Service
public class OptionServiceImpl extends BaseServiceImpl<Option> implements OptionService {
    public OptionServiceImpl(OptionRepository optionRepository) {
        super(optionRepository);
    }
}
