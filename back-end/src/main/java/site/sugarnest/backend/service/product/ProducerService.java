package site.sugarnest.backend.service.product;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import site.sugarnest.backend.dto.dto.ProducerDto;
import site.sugarnest.backend.entities.ProducerEntity;
import site.sugarnest.backend.mapper.IProducerMapper;
import site.sugarnest.backend.reponsitoties.IProducerRepository;

import java.util.List;
@AllArgsConstructor
@Service
public class ProducerService {
    IProducerRepository producerRepository;
    IProducerMapper producerMapper;

    public boolean checkProducerExistByName(String name) {
        return producerRepository.existsByNameProducer(name);
    }
    public ProducerDto createProducer(ProducerDto producerDto) {
        ProducerEntity producerEntity = producerMapper.mapToProducer(producerDto);
        return producerMapper.mapToProducerDto(producerRepository.save(producerEntity));
    }

    public List<ProducerDto> getAllProducers() {
        return producerRepository.findAll().stream().map(producerMapper::mapToProducerDto).toList();
    }

    public ProducerDto getProducerById(Long id) {
        return producerMapper.mapToProducerDto(producerRepository.findById(id).orElseThrow());
    }

    public ProducerDto updateProducer(ProducerDto producerDto) {
        ProducerEntity producerEntity = producerMapper.mapToProducer(producerDto);
        return producerMapper.mapToProducerDto(producerRepository.save(producerEntity));
    }

    public void deleteProducer(Long id) {
        producerRepository.deleteById(id);
    }
}
