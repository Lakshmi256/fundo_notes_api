package com.bridgelabz.fundoonotes.implementation;
/*
 * author:Lakshmi Prasad A
 */
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.dto.LabelUpdate;
import com.bridgelabz.fundoonotes.entity.LabelInformation;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.repository.LabelRepository;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.LabelService;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

@Service
public class LabelServiceImplementation implements LabelService {

	private UserInformation user = new UserInformation();
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtGenerator tokenGenerator;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private LabelRepository labelRepository;
	@Autowired
	private NoteRepository noteRepository;
	private LabelInformation labelInformation;

	@Transactional
	@Override
	public void createLabel(LabelDto label, String token) {
		Long id = null;
		try {
			id = (Long) tokenGenerator.parseJWT(token);

		} catch (Exception e) {
			throw new UserException("user does not exist");

		}
		UserInformation user = userRepository.getUserById(id);
		if (user != null) {
			LabelInformation labelInfo = labelRepository.fetchLabel(user.getUserId(), label.getName());
			if (labelInfo == null) {
				labelInformation = modelMapper.map(label, LabelInformation.class);

				labelInformation.setUserId(user.getUserId());
				labelRepository.save(labelInformation);
			} else {
				throw new UserException("label with that name is already present ");
			}
		} else {
			throw new UserException("note does not exist with userid");
		}
	}

	@Override
	public void createAndMap(LabelDto label, String token, Long noteId) {
		Long id = null;
		try {
			id = (Long) tokenGenerator.parseJWT(token);

		} catch (Exception e) {
			throw new UserException("user does not exist");

		}
		UserInformation user = userRepository.getUserById(id);
		if (user != null) {
			LabelInformation labelInfo = labelRepository.fetchLabel(user.getUserId(), label.getName());
			if (labelInfo == null) {
				BeanUtils.copyProperties(label, LabelInformation.class);
				labelInformation.setUserId(user.getUserId());
				labelRepository.save(labelInformation);
				NoteInformation note = noteRepository.findById(noteId);
				note.getList().add(labelInformation);
				noteRepository.save(note);
			} else {
				throw new UserException("label with that name is already present ");
			}
		} else {
			throw new UserException("note does not exist with userid");
		}
	}

	@Override
	public void addLabel(Long labelId, String token, Long noteId) {
		NoteInformation note = noteRepository.findById(noteId);
		LabelInformation label = labelRepository.fetchLabelById(labelId);
		label.getList().add(note);
		labelRepository.save(label);
	}

	@Override
	public void removeLabel(Long labelId, String token, Long noteId) {
		NoteInformation note = noteRepository.findById(noteId);
		LabelInformation label = labelRepository.fetchLabelById(labelId);
		label.getList().remove(note);
		labelRepository.save(label);
	}

	@Override
	public void editLabel(LabelUpdate label, String token) {
		Long id = null;
		try {
			id = (Long) tokenGenerator.parseJWT(token);

		} catch (Exception e) {
			throw new UserException("user does not exist");

		}
		UserInformation user = userRepository.getUserById(id);
		if (user != null) {
			LabelInformation labelInfo = labelRepository.fetchLabelById(label.getLabelId());
			if (labelInfo != null) {
				labelInfo.setName(label.getLabelName());
				labelRepository.save(labelInfo);
			} else {
				throw new UserException("labelwith name does not exist");
			}
		} else {
			throw new UserException("user does not exist");
		}
	}

	@Override
	public void deleteLabel(LabelUpdate label, String token) {
		Long id = null;
		try {
			id = (Long) tokenGenerator.parseJWT(token);

		} catch (Exception e) {
			throw new UserException("user does not exist");

		}
		UserInformation user = userRepository.getUserById(id);
		if (user != null) {
			LabelInformation labelInfo = labelRepository.fetchLabelById(label.getLabelId());
			if (labelInfo != null) {
				;
				labelRepository.deleteLabel(label.getLabelId());
			} else {
				throw new UserException("labelwith name does not exist");
			}
		} else {
			throw new UserException("user does not exist");
		}
	}

	@Override
	public List<LabelInformation> getLabel(String token) {
		Long id = null;
		try {
			id = (Long) tokenGenerator.parseJWT(token);

		} catch (Exception e) {
			throw new UserException("user does not exist");

		}
		List<LabelInformation> labels = labelRepository.getAllLabel(id);
		return labels;
	}
	@Override
	public List<NoteInformation> getAllNotes(String token ,Long labelId){
		LabelInformation label=labelRepository.getLabel(labelId);
		List<NoteInformation> list = label.getList();
		return list;
	}
}
